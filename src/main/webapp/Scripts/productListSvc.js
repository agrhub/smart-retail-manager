/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
var SERVER = "https://mallhub.appspot.com/";
angular.module('todoApp')
    .factory('productListSvc', ['$http', function ($http) {
        return {
            getItems: function () {
                return $http.get(SERVER + 'api/products');
            },
            getItem: function (id) {
                return $http.get(SERVER + 'api/product/' + id);
            },
            postItem: function (item) {
                return $http.post(SERVER + 'api/product', item);
            },
            putItem: function (item) {
                return $http.put(SERVER + 'api/product', item);
            },
            deleteItem: function (id) {
                return $http({
                    method: 'DELETE',
                    url: SERVER + 'api/product/' + id
                });
            }
        };
    }]);
