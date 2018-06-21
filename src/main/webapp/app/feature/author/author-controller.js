"use strict";

(function() {

    var AuthorController =  function(authorService, $log) {
        
    	var vm = this;

        vm.createMode=false;
        vm.createName = "";
        vm.toggleCreateMode = function() {
            vm.createMode=!vm.createMode;
        }

        vm.create = function() {
            console.log({"name":vm.createName});
            authorService.saveAuthor({"name":vm.createName});
            vm.toggleCreateMode();
            vm.createName = "";
            authorService.getAuthors().then(function (results) {
            vm.authors = results;});
        }

        vm.delete = function(authorToDelete) {
            authorService.deleteAuthor(authorToDelete);
            authorService.getAuthors().then(function (results) {
            vm.authors = results;});
        }

        function init() {
        	authorService.getAuthors().then(function (results) {
           	vm.authors = results;
           	$log.log("In the author controller the value of the result promise is ");
        	$log.log(JSON.stringify(vm.authors));
            }, function (error) {
                vm.error = true;
                vm.errorMessage = error;
            });
       }
       
       init();

    };

    angular.module('bookApp').controller('authorController', ['authorService','$log', AuthorController]);
}());