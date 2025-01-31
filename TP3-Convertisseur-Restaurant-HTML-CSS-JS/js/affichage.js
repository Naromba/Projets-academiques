function affichage() {
    var nom = document.getElementById("NOM").value;
    var niveau = document.getElementById("valeur").value;
    var affiche = document.getElementById("aff");

    let content;

    if (niveau < 1 || niveau > 6) {
        console.log('inside if');
        content = "<p>Erreur le niveau doit être entre 1 et 6</p>";

        $(affiche).removeClass("green");
        $(affiche).addClass("red");
    } else {
        content = "<h" + niveau + ">Bonjour" + " " + nom + " " + "niveau =" + " " + niveau + "</h" + niveau + ">";

        $(affiche).removeClass("red");
        $(affiche).addClass("green");
    }

    document.getElementById("aff").innerHTML = content;
}