/*function realizado(){

	alert('Compra Realizada Correctamente');
	console.log("si ejecuta");
}*/

$(document).ready(function(){
    $("#fin").click(function(){
    	setTimeout(function() {
        $("#mostrar").slideDown("slow", function() {
        	$(".Completadojs").css("justify-content:", "center");
        	$(".Completadojs").css("align-items:", "center");
        	$(".Completadojs").css("display:", "flex");

        });(1500);
	    });
	    setTimeout(function() {
	        $("#mostrar").slideUp("slow");
	    },3000);
    });
});