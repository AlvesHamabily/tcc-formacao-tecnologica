// Verifica se a senha é forte
function senhaValida(p) {
  const letrasMaiusculas = /[A-Z]/;
  const letrasMinusculas = /[a-z]/;
  const numeros = /[0-9]/;
  const caracteresEspeciais = /[!@#$%^&*()\-_=+{};:,<.>]/;

  if (p.length < 8) return false;

  let temMaiuscula = false;
  let temMinuscula = false;
  let temNumero = false;
  let temEspecial = false;

  for (let i = 0; i < p.length; i++) {
    if (letrasMaiusculas.test(p[i])) temMaiuscula = true;
    else if (letrasMinusculas.test(p[i])) temMinuscula = true;
    else if (numeros.test(p[i])) temNumero = true;
    else if (caracteresEspeciais.test(p[i])) temEspecial = true;
  }

  return temMaiuscula && temMinuscula && temNumero && temEspecial;
}

// Valida o cadastro completo
function validarCadastro() {
  const senha = document.getElementById("senha").value;
  const confirmarSenha = document.getElementById("confirmar_senha").value;
  const termos = document.getElementById("termos");

  if (!senhaValida(senha)) {
    alert("Senha fraca! A senha deve conter pelo menos 8 caracteres, incluindo letra maiúscula, minúscula, número e caractere especial.");
    return false;
  }

  if (senha !== confirmarSenha) {
    alert("As senhas não coincidem!");
    return false;
  }

  if (!termos.checked) {
    alert("Você deve aceitar os termos de política e privacidade para continuar.");
    return false;
  }

  return true;
}

// Mostra ou esconde o card de termos
function mostrarTermos(event) {
  event.preventDefault();
  const card = document.getElementById("card-termos");
  card.style.display = (card.style.display === "none" || card.style.display === "") ? "block" : "none";
}

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

  // Caso queira adicionar mais códigos de erro depois:
  // if (params.get("error") === "2") alert("Outro tipo de erro...");
});
