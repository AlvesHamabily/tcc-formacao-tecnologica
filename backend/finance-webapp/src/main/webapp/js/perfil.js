// Obtém os valores do localStorage
let points = parseInt(localStorage.getItem("points"));
let nivel = localStorage.getItem("nivel");

// Define valores padrão se forem nulos
if (isNaN(points)) points = 0;
if (!nivel) nivel = "Iniciante";

// Atualiza os elementos do HTML
document.getElementById("pontos").textContent = points;
document.getElementById("nivel").textContent = nivel;

// Define cookies válidos para a sessão (sem expiração = expiram ao fechar o navegador)
document.cookie = `points=${points}; path=/`;
document.cookie = `nivel=${nivel}; path=/`;

//---------------------

// Verifica se o jQuery foi carregado via CDN; se não, carrega localmente
if (!window.jQuery) {
  const fallbackScript = document.createElement('script');
  fallbackScript.src = '../../assets/js/vendor/jquery-slim.min.js';
  document.head.appendChild(fallbackScript);
}

// Substitui os ícones Feather
document.addEventListener("DOMContentLoaded", function () {
  if (window.feather) {
    feather.replace();
  }

  // Inicializa o gráfico
  const ctx = document.getElementById("myChart");
  if (ctx && window.Chart) {
    const myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        datasets: [{
          data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
          lineTension: 0,
          backgroundColor: 'transparent',
          borderColor: '#007bff',
          borderWidth: 4,
          pointBackgroundColor: '#007bff'
        }]
      },
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: false
            }
          }]
        },
        legend: {
          display: false,
        }
      }
    });
  }
});
