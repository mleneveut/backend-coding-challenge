"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", []);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "config", "restalchemy", function ExpensesCtrl($rootScope, $scope, $config, $restalchemy) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";

	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);

    var restExpenses = $restalchemy.init({root: $config.apiroot}).at("expenses");
    var restConversionRatesEuro = $restalchemy.init({root: $config.apiroot}).at("conversion-rates/euro");

	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		restExpenses.get().then(function(expenses) {
			$scope.expenses = expenses;
		});
    };

	$scope.saveExpense = function() {
		if ($scope.expensesform.$valid) {

            if ($scope.newExpense.amount.indexOf('EUR') > 0) {
                restConversionRatesEuro.get().then(function (rate) {
                    $scope.newExpense.amount = parseFloat($scope.newExpense.amount.substring(0, $scope.newExpense.amount.indexOf('EUR'))) * parseFloat(rate);

                    callExpenses();
                });
            } else {
                callExpenses()
            }
        }
    };

    //didn't catch how to do sync calls with restAlchemy, so extracting this call
    var callExpenses = function () {
        // Post the expense via REST
        restExpenses.post($scope.newExpense).then(function () {
            // Reload new expenses list
            loadExpenses();
            $scope.clearExpense();
        })
            .error(function (err) {
                $scope.errorCreation = err.description;
            });
    };

	$scope.clearExpense = function() {
		$scope.newExpense = {};
        $scope.errorCreation = '';
	};

    $scope.calculateVAT = function () {
        let amount = $scope.newExpense.amount;
        if (!amount) {
            $scope.newExpense.vat = '';
        } else {
            if (amount.indexOf('EUR') > 0) {
                amount = amount.substring(0, $scope.newExpense.amount.indexOf('EUR'));
            }
            let vatFloat = parseFloat($config.vat);
            $scope.newExpense.vat = amount / (1 + vatFloat) * vatFloat;
        }
    };

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
