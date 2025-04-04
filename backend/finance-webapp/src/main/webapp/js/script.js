let videos = [];
let currentIndex = 0;
let player;

// Garante que a função fique global
window.onYouTubeIframeAPIReady = function () {
    console.log("API do YouTube carregada.");
    carregarVideos();
};

function bloquearBotaoAvancar() {
    const nextButton = document.getElementById('nextButton');
    nextButton.disabled = true; // Bloqueia inicialmente

    // Monitorar o progresso do vídeo
    const monitorarProgresso = setInterval(() => {
        const duration = player.getDuration();
        const currentTime = player.getCurrentTime();
        const progress = (currentTime / duration) * 100;

        if (progress >= 80) {
            nextButton.disabled = false; // Desbloqueia o botão
            clearInterval(monitorarProgresso); // Para o monitoramento
        }
    }, 1000); // Checa a cada segundo
}

// Carrega a lista de vídeos do backend
function carregarVideos() {
	console.log("Chamando carregarVideos()...");  // LOG PARA TESTE
    fetch('/FinEduca/videos')
        .then(response => response.json())
        .then(data => {
			console.log("Vídeos recebidos:", data);  // LOG PARA TESTE
            if (!Array.isArray(data)) {
                console.error("Resposta inesperada da API:", data);
                return;
            }

			console.log("Populando lista de vídeos:", videos);
			
            videos = data;
            if (videos.length > 0) {
                carregarListaVideos();
                carregarVideo(0); // Carrega o primeiro vídeo
            } else {
                console.warn("Nenhum vídeo encontrado na API.");
            }
        })
        .catch(error => console.error('Erro ao buscar vídeos:', error));
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
            videoId: videoId,
            events: {
                onStateChange: (event) => {
                    if (event.data === YT.PlayerState.PLAYING) {
                        bloquearBotaoAvancar(); // Liga o monitoramento ao vídeo atual
                    }
                }
            }
        });
    } else {
        console.log("Carregando novo vídeo:", videoId);
        player.loadVideoById(videoId);

        // Monitorar o progresso novamente para o novo vídeo
        bloquearBotaoAvancar();
    }

    // Bloqueia o botão "Avançar" para vídeos futuros
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

