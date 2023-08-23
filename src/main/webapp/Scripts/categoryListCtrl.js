/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .controller('categoryListCtrl', ['$scope', '$location', 'categoryListSvc', function ($scope, $location, categoryListSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.list = null;
        $scope.editingInProgress = false;
        $scope.newProductCaption = '';

        $scope.editInProgressProduct = {
            id: 0,
            name: '',
            images: '',
            description: '',
            priority: 0,
            finish: false
        };

        $scope.finishSwitch = function (item) {
            categoryListSvc.putItem(item).error(function (err) {
                item.finished = !item.finished;
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.editSwitch = function (item) {
            item.edit = !item.edit;
            if (item.edit) {
                $scope.editInProgressProduct.id = item.id;
                $scope.editInProgressProduct.name = item.name;
                $scope.editInProgressProduct.images = item.images;
                $scope.editInProgressProduct.description = item.description;
                $scope.editInProgressProduct.priority = item.priority;
                $scope.editInProgressProduct.finished = item.finished;
                $scope.editingInProgress = true;
            } else {
                $scope.editingInProgress = false;
            }
        };

        $scope.populate = function () {
            categoryListSvc.getItems().success(function (results) {
                $scope.list = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.delete = function (id) {
            categoryListSvc.deleteItem(id).success(function (results) {
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.update = function (item) {
            var updateData = {
                id: item.id,
                name: $scope.editInProgressProduct.name,
                images: $scope.editInProgressProduct.images,
                description: $scope.editInProgressProduct.description,
                priority: $scope.editInProgressProduct.priority
            };
            item.name = $scope.editInProgressProduct.name;
            item.images = $scope.editInProgressProduct.images;
            item.description = $scope.editInProgressProduct.description;
            item.priority = $scope.editInProgressProduct.priority;
            categoryListSvc.postItem(updateData).success(function (results) {
                $scope.populate();
                $scope.editSwitch(item);
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.add = function () {
            function getUser() {
                var user = localStorage.getItem('user') || 'unknown';
                localStorage.setItem('user', user);
                return user;
            }

            categoryListSvc.putItem({
                'description': $scope.newProductCaption,
                'owner': getUser(),
                'finish': 'false'
            }).success(function (results) {
                $scope.newProductCaption = '';
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMsg = '';
            })
        };
    }]);
