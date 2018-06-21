"use strict";

(function () {

    function AuthorDal (dal) {

        this.getAuthors = function () {
            return dal.http.GET("rest/author/json");
        };

        this.saveAuthor = function (authorToSave) {
            return dal.http.POST("rest/author/json", authorToSave);
        };

        this.updateAuthor = function (authorToUpdate) {
            return dal.http.PUT("rest/author/json/", authorToUpdate);
        };

        this.deleteAuthor = function (authorToDelete) {
            return dal.http.DELETE("rest/author/json/", authorToDelete);
        };
    }
    
    angular.module("bookApp").service("authorDal", ["dal", AuthorDal]);
}());