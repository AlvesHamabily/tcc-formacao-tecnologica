// Log para confirmar que o arquivo JavaScript foi carregado
console.log("videos.js loaded");

let videos = [];
let videoAtualIndex = 0;
let player;
let videoCompleted = false;
let intervalID;
let playerReady = false;

// Função responsável por buscar os vídeos do endpoint
async function fetchVideos() {
  try {
    const response = await fetch("videos");
    videos = await response.json();

    // Se o endpoint não retornar vídeos, utilize um vídeo padrão
    if (!Array.isArray(videos) || videos.length === 0) {
      console.warn("Nenhum vídeo retornado. Usando vídeo padrão.");
      videos = [{ id: "JmTv8C4xXyE-VE", titulo: "Vídeo 1" }];
    }
  } catch (error) {
    console.error("Erro ao buscar vídeos:", error);
    videos = [{ id: "JmTv8C4xXyE-VE", titulo: "Vídeo 1" }];
  }
  
  populateVideoList();
  loadVideo(0);
}

// Popula a lista de vídeos na sidebar
function populateVideoList() {
  const videoList = document.getElementById("videoList");
  videoList.innerHTML = "";
  videos.forEach((video, index) => {
    const li = document.createElement("li");
    const button = document.createElement("button");
    button.innerText = video.titulo;
    button.onclick = () => loadVideo(index);
    li.appendChild(button);
    videoList.appendChild(li);
  });
}

// Carrega o vídeo selecionado e atualiza o título
function loadVideo(index) {
  const video = videos[index];
  // Se o player já estiver com o mesmo vídeo, apenas atualize o título e índice
  if (player && player.getVideoData && player.getVideoData().video_id === video.id) {
    document.getElementById("videoTitle").innerText = video.titulo;
    videoAtualIndex = index;
    return;
  }
  initializePlayer(video.id);
  document.getElementById("videoTitle").innerText = video.titulo;
  videoAtualIndex = index;
}

// Inicializa (ou reinicializa) o player do YouTube
function initializePlayer(videoId) {
  if (!playerReady) {
    console.error("API do YouTube ainda não está carregada.");
    return;
  }
  console.log("Inicializando player com vídeo id:", videoId);
  if (player) {
    player.destroy();
  }
  player = new YT.Player("videoFrame", {
    videoId: videoId,
    events: {
      onReady: onPlayerReady,
      onStateChange: onPlayerStateChange
    }
  });
  console.log("Player inicializado:", player);
}

// Callback chamado quando o player está pronto
function onPlayerReady(event) {
  console.log("onPlayerReady chamado");
  document.getElementById("nextButton").disabled = true;
  
  if (intervalID) clearInterval(intervalID);
  // Inicia a verificação do progresso após 1 segundo
  setTimeout(() => {
    intervalID = setInterval(checkVideoProgress, 1000);
  }, 1000);
}

// Callback para monitorar mudanças de estado do player
function onPlayerStateChange(event) {
  console.log("onPlayerStateChange:", event.data);
  if (event.data === YT.PlayerState.ENDED) {
    console.log("O vídeo terminou");
    videoCompleted = true;
    document.getElementById("nextButton").disabled = false;
    if (intervalID) clearInterval(intervalID);
  }
}

// Verifica periodicamente o progresso do vídeo
function checkVideoProgress() {
  if (player && typeof player.getCurrentTime === "function") {
    const currentTime = player.getCurrentTime();
    const duration = player.getDuration();
    console.log("Progresso:", currentTime, "/", duration);
    if (duration > 0 && (currentTime / duration) >= 0.80) {
      document.getElementById("nextButton").disabled = false;
      console.log("80% assistido, botão ativado.");
      clearInterval(intervalID);
    }
  } else {
    console.log("player.getCurrentTime não disponível");
  }
}

// Função para navegar entre vídeos
function navigate(direction) {
  if (direction === "prev" && videoAtualIndex > 0) {
    loadVideo(videoAtualIndex - 1);
  } else if (direction === "next" && videoAtualIndex < videos.length - 1) {
    loadVideo(videoAtualIndex + 1);
  }
}
window.navigate = navigate;

// Função chamada automaticamente pela API do YouTube quando está pronta
window.onYouTubeIframeAPIReady = function() {
  console.log("API do YouTube carregada");
  playerReady = true;
  fetchVideos();
};

// Log adicional para confirmar que o DOM foi carregado
document.addEventListener("DOMContentLoaded", () => {
  console.log("DOM fully loaded");
});
