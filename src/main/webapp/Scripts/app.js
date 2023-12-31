/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp', ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/ProductList', {
            controller: 'productListCtrl',
            templateUrl: 'Views/ProductList.html',
        }).when('/CategoryList', {
            controller: 'categoryListCtrl',
            templateUrl: 'Views/CategoryList.html',
        }).when('/DeviceList', {
            controller: 'deviceListCtrl',
            templateUrl: 'Views/DeviceList.html',
        }).otherwise({redirectTo: '/ProductList'});
        // $routeProvider.when('/Home', {
        //     controller: 'homeCtrl',
        //     templateUrl: 'Views/Home.html',
        // }).when('/ProductList', {
        //     controller: 'productListCtrl',
        //     templateUrl: 'Views/ProductList.html',
        // }).when('/TodoList', {
        //     controller: 'todoListCtrl',
        //     templateUrl: 'Views/TodoList.html',
        // }).otherwise({redirectTo: '/Home'});
    }]);
