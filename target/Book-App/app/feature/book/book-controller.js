"use strict";

(function() {

    var BookController =  function(bookService, $log, $scope) {
        
    	var vm = this;

        vm.createMode=false;
        vm.createTitle = "";
        vm.createGenre = "";
        vm.createYear = "";
        vm.toggleCreateMode = function() {
            vm.createMode=!vm.createMode;
        }

        vm.create = function() {
            console.log({"bookTitle":vm.createTitle, "genre":vm.createGenre, "publishYear":vm.createYear});
            bookService.saveBook({"bookTitle":vm.createTitle, "genre":vm.createGenre, "publishYear":vm.createYear});
            vm.toggleCreateMode();
            vm.createTitle="";
            vm.createGenre="";
            vm.createYear="";
            bookService.getBooks().then(function (results) {
            vm.books = results;});
        }

        vm.delete = function(bookToDelete) {
            bookService.deleteBook(bookToDelete);
            bookService.getBooks().then(function (results) {
            vm.books = results;});
        }

        function init() {
        	bookService.getBooks().then(function (results) {
           	vm.books = results;
           	$log.log("In the book controller the value of the result promise is ");
        	$log.log(JSON.stringify(vm.books));
            }, function (error) {
                vm.error = true;
                vm.errorMessage = error;
            });
       }
       
       init();

    };

    angular.module('bookApp').controller('bookController', ['bookService','$log', BookController]);
}());