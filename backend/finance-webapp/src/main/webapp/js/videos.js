document.addEventListener("DOMContentLoaded", async () => {
    let videos = [];
    let videoAtualIndex = 0;

    async function fetchVideos() {
        try {
            const response = await fetch("videos");
            videos = await response.json();
            populateVideoList();
            if (videos.length > 0) loadVideo(0);
        } catch (error) {
            console.error("Erro ao buscar vídeos:", error);
        }
    }

    function populateVideoList() {
        let videoList = document.getElementById("videoList");
        videoList.innerHTML = "";
        videos.forEach((video, index) => {
            let li = document.createElement("li");
            let button = document.createElement("button");
            button.innerText = video.titulo;
            button.onclick = () => loadVideo(index);
            li.appendChild(button);
            videoList.appendChild(li);
        });
    }

    function loadVideo(index) {
        let video = videos[index];
		
		let videoUrl = `${video.url}?autoplay=1&controls=0&modestbranding=1&enablejsapi=1`;

		
        document.getElementById("videoFrame").src = video.url;
        document.getElementById("videoTitle").innerText = video.titulo;
        videoAtualIndex = index;
    }

    function navigate(direction) {
        if (direction === 'prev' && videoAtualIndex > 0) {
            loadVideo(videoAtualIndex - 1);
        } else if (direction === 'next' && videoAtualIndex < videos.length - 1) {
            loadVideo(videoAtualIndex + 1);
        }
    }

    window.navigate = navigate;
    fetchVideos();
});
