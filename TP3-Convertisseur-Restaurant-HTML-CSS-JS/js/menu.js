$(document).ready(function () {
    var monMenu = { "Escargot": { cat: "Entree", image: "escargot.jpg", prix: 5.50 }, "Salade verte": { cat: "Entree", image: "salade2.jpg", prix: 5.95 }, "Salade César": { cat: "Entree", image: "salade.jpg", prix: 6.95 }, "Spaghetti": { cat: "Principal", image: "spaghetti.jpg", prix: 15.95 }, "Steak": { cat: "Principal", image: "steak.jpg", prix: 25.95 }, "Pizza": { cat: "Principal", image: "pizza.jpg", prix: 17.95 }, "Crème glacée": { cat: "Dessert", image: "cremeglacee.jpg", prix: 4.25 }, "Gateau": { cat: "Dessert", image: "gateau.jpg", prix: 4.95 }, "Pouding": { cat: "Dessert", image: "pouding.jpg", prix: 3.95 }, "Café / Thé": { cat: "Boisson", image: "cafe.jpg", prix: 2.50 }, "Boisson gazeuse": { cat: "Boisson", image: "boisson.jpg", prix: 2.95 } };

    $('li').on('click', function (e) {
        if ($(this).hasClass('choisir'))
            return; // on ne fait rien il est déjà sélectionné
        var element = $(this).parent();
        element.find('li').removeClass('choisir').find('ul').hide();
        $(this).addClass('choisir').children('ul').slideToggle(500);
    });

    $('li li').on('click', function (e) {
        var choixMenu = monMenu[$(this).html()];
        var idImg = "#img" + choixMenu["cat"];
        var nomFichierImg = "images/" + choixMenu["image"];
        var idPrix = "#prix" + choixMenu["cat"];
        var prix = choixMenu["prix"];


        $(idPrix).html(prix + '$');
        $(idImg).attr("src", nomFichierImg);

    });

    $('button').on('click', function (e) {
        var prixEntree = parseFloat($("#prixEntree").html());
        var prixPrincipal = parseFloat($("#prixPrincipal").html());
        var prixDessert = parseFloat($("#prixDessert").html());
        var prixBoisson = parseFloat($("#prixBoisson").html());

        if (isNaN(prixEntree)) prixEntree = 0;
        if (isNaN(prixPrincipal)) prixPrincipal = 0;
        if (isNaN(prixDessert)) prixDessert = 0;
        if (isNaN(prixBoisson)) prixBoisson = 0;

        var total = prixEntree + prixPrincipal + prixDessert + prixBoisson;
        var totalAvecTaxes = (total * 0.14975);
        var totalApresTaxes = total + totalAvecTaxes;

        var totalText = "Total: " + total.toFixed(2) + "$ taxes: " + totalAvecTaxes.toFixed(2) + "$ = " + totalApresTaxes.toFixed(2) + "$";

        $("#total").html(totalText);
    });
});