/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .controller('productListCtrl', ['$scope', '$location', 'productListSvc', function ($scope, $location, productListSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.productList = null;
        $scope.editingInProgress = false;
        $scope.newProductCaption = '';

        $scope.editInProgressProduct = {
            code: '',
            name: '',
            description: '',
            price: 0,
            discount: 0,
            unit: '',
            category_id: '',
            child_products: '',
            id: 0,
            finish: false
        };

        $scope.finishSwitch = function (product) {
            productListSvc.putItem(product).error(function (err) {
                product.finished = !product.finished;
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.editSwitch = function (product) {
            product.edit = !product.edit;
            if (product.edit) {
                $scope.editInProgressProduct.code = product.code;
                $scope.editInProgressProduct.name = product.name;
                $scope.editInProgressProduct.description = product.description;
                $scope.editInProgressProduct.price = product.price;
                $scope.editInProgressProduct.discount = product.discount;
                $scope.editInProgressProduct.unit = product.unit;
                $scope.editInProgressProduct.category_id = product.category_id;
                $scope.editInProgressProduct.child_products = product.child_products;
                $scope.editInProgressProduct.id = product.id;
                $scope.editInProgressProduct.finished = product.finished;
                $scope.editingInProgress = true;
            } else {
                $scope.editingInProgress = false;
            }
        };

        $scope.populate = function () {
            productListSvc.getItems().success(function (results) {
                for (var i = 0; i < results.length; i++) {
                    results[i].images = JSON.parse(results[i].images)
                    if (results[i].images.length > 0) {
                        results[i].avatar = results[i].images[0];
                    } else {
                        results[i].avatar = '/Images/no-image-slide.png';
                    }
                    results[i].tags = JSON.parse(results[i].tags);
                    if (results[i].discount > 0) {
                        results[i].hasDiscount = true;
                        results[i].currentPrice = results[i].price - Math.round(results[i].price * results[i].discount / 100);
                    } else {
                        results[i].hasDiscount = false;
                        results[i].currentPrice = results[i].price;
                    }
                }
                $scope.productList = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.delete = function (id) {
            productListSvc.deleteItem(id).success(function (results) {
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.update = function (product) {
            var updateProductData = {
                id: product.id,
                code: product.code,
                name: $scope.editInProgressProduct.name,
                images: JSON.stringify(product.images),
                price: $scope.editInProgressProduct.price,
                currency: product.currency,
                discount: $scope.editInProgressProduct.discount,
                description: $scope.editInProgressProduct.description,
                unit: $scope.editInProgressProduct.unit,
                tags: JSON.stringify(product.tags),
                category_id: $scope.editInProgressProduct.category_id,
                child_products: product.child_products
            };
            product.name = $scope.editInProgressProduct.name;
            product.description = $scope.editInProgressProduct.description;
            product.price = $scope.editInProgressProduct.price;
            product.discount = $scope.editInProgressProduct.discount;
            product.unit = $scope.editInProgressProduct.unit;
            productListSvc.postItem(updateProductData).success(function (results) {
                $scope.populate();
                $scope.editSwitch(product);
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

            productListSvc.putItem({
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
