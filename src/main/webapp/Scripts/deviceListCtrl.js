/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .controller('deviceListCtrl', ['$scope', '$location', 'deviceListSvc', function ($scope, $location, deviceListSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.list = null;
        $scope.editingInProgress = false;
        $scope.newProductCaption = '';

        $scope.editInProgressProduct = {
            id: '',
            name: '',
            mac_address: '',
            type: 0,
            product_id: 0,
            draw_mode: '',
            finish: false
        };

        $scope.finishSwitch = function (item) {
            deviceListSvc.putItem(item).error(function (err) {
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
                $scope.editInProgressProduct.mac_address = item.mac_address;
                $scope.editInProgressProduct.type = item.type;
                $scope.editInProgressProduct.product_id = item.product_id;
                $scope.editInProgressProduct.draw_mode = item.draw_mode;
                $scope.editInProgressProduct.finished = item.finished;
                $scope.editingInProgress = true;
            } else {
                $scope.editingInProgress = false;
            }
        };

        $scope.populate = function () {
            deviceListSvc.getItems().success(function (results) {
                $scope.list = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.delete = function (id) {
            deviceListSvc.deleteItem(id).success(function (results) {
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.update = function (item) {
            var updateProductData = {
                id:  $scope.editInProgressProduct.id,
                name: $scope.editInProgressProduct.name,
                mac_address: $scope.editInProgressProduct.mac_address,
                type: $scope.editInProgressProduct.type,
                product_id: $scope.editInProgressProduct.product_id,
                draw_mode: $scope.editInProgressProduct.draw_mode
            };
            item.name = $scope.editInProgressProduct.name;
            item.mac_address = $scope.editInProgressProduct.mac_address;
            item.type = $scope.editInProgressProduct.type;
            item.product_id = $scope.editInProgressProduct.product_id;
            item.draw_mode = $scope.editInProgressProduct.draw_mode;
            deviceListSvc.postItem(updateProductData).success(function (results) {
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

            deviceListSvc.putItem({
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
