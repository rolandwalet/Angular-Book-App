"use strict";

(function () {

    
    function AuthorService (authorDal) {

        this.getAuthors = function()
        {
        	return authorDal.getAuthors();
        };
        
        this.deleteAuthor = function(authorToDelete)
        {
        	return authorDal.deleteAuthor(authorToDelete);
        };

        this.saveAuthor = function(authorToSave)
        {
        	return authorDal.saveAuthor(authorToSave);
        };

    }
    
    angular.module("bookApp").service("authorService", ['authorDal', AuthorService]);

}());