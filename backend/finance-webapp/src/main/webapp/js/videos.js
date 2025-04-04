let videos = [];
let currentIndex = 0;
let player;

// Garante que a função fique global
window.onYouTubeIframeAPIReady = function () {
    console.log("API do YouTube carregada.");
    carregarVideos();
};

// Carrega a lista de vídeos do backend
function carregarVideo(index) {
    if (index < 0 || index >= videos.length) return;

    currentIndex = index;
    document.getElementById('videoTitle').textContent = videos[index].titulo;

    let videoId = extrairIdYoutube(videos[index].url);
    if (!videoId) {
        console.error('ID do vídeo inválido:', videos[index].url);
        return;
    }

    if (!player) {
        console.log("Criando novo player do YouTube...");
        player = new YT.Player('videoFrame', {
            height: '450',
            width: '800',
            videoId: videoId,
            events: {
                onReady: bloquearBotaoAvancar // Liga o monitoramento ao carregamento
            }
        });
    } else {
        console.log("Carregando novo vídeo:", videoId);
        player.loadVideoById(videoId);
        bloquearBotaoAvancar(); // Liga o monitoramento ao novo vídeo
    }

    document.getElementById('nextButton').disabled = (currentIndex >= videos.length - 1);
}

function bloquearBotaoAvancar() {
    const nextButton = document.getElementById('nextButton');
    nextButton.disabled = true; // Bloqueia inicialmente

    player.addEventListener('onStateChange', function (event) {
        if (event.data === YT.PlayerState.PLAYING) {
            const interval = setInterval(() => {
                const duration = player.getDuration();
                const currentTime = player.getCurrentTime();
                const progress = (currentTime / duration) * 100;

                if (progress >= 80) {
                    nextButton.disabled = false; // Desbloqueia o botão
                    clearInterval(interval); // Para o monitoramento
                }
            }, 1000); // Checa a cada segundo
        }
    });
}

// Popula a lista lateral de vídeos
function carregarListaVideos() {
    const videoList = document.getElementById('videoList');
    if (!videoList) {
        console.error("Elemento #videoList não encontrado no DOM.");
        return;
    }
    videoList.innerHTML = '';

    videos.forEach((video, index) => {
        let li = document.createElement('li');
        li.textContent = video.titulo;
        li.style.cursor = "pointer";
        li.onclick = () => carregarVideo(index);
        videoList.appendChild(li);
    });
}

// Extrai o ID do YouTube a partir da URL
function extrairIdYoutube(url) {
    if (url.includes("youtube.com") || url.includes("youtu.be")) {
        let match = url.match(/(?:youtube\.com\/watch\?v=|youtu\.be\/|youtube\.com\/embed\/)([\w-]+)/);
        return match ? match[1] : null;
    }
    return url; 
}


// Carrega um vídeo no player
function carregarVideo(index) {
    if (index < 0 || index >= videos.length) return;
    
    currentIndex = index;
    document.getElementById('videoTitle').textContent = videos[index].titulo;
    
    let videoId = extrairIdYoutube(videos[index].url);
    if (!videoId) {
        console.error('ID do vídeo inválido:', videos[index].url);
        return;
    }

    if (!player) {
        console.log("Criando novo player do YouTube...");
        player = new YT.Player('videoFrame', {
            height: '450',
            width: '800',
            videoId: videoId
        });
    } else {
        console.log("Carregando novo vídeo:", videoId);
        player.loadVideoById(videoId);
    }
    
    // Atualiza botão "Avançar"
    document.getElementById('nextButton').disabled = (currentIndex >= videos.length - 1);
}

// Navega entre os vídeos
function navigate(direction) {
    if (direction === 'prev' && currentIndex > 0) {
        carregarVideo(currentIndex - 1);
    } else if (direction === 'next' && currentIndex < videos.length - 1) {
        carregarVideo(currentIndex + 1);
    }
}

document.addEventListener("DOMContentLoaded", carregarVideos);

