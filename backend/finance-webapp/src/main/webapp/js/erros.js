// Alerts personalizados com base nos parâmetros da URL
window.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);

  if (params.get("register") === "success") {
	Swal.fire({
	  icon: 'success',
	  title: 'Cadastro realizado!',
	  text: 'Sua conta foi criada com sucesso',
	});
  }

  if (params.get("error") === "1") {
	Swal.fire({
	  icon: 'error',
	  title: 'Oops...',
	  text: 'Usuário já cadastrado!'
	});
  }

  if (params.get("error") === "3") {
  Swal.fire({
    icon: 'error',
    title: 'Oops...',
    text: 'Autenticação falhou'
  });
  }
});
