/**
 * Created by Sandeep on 01/06/14.
 */

angular.module('testRest',['ui.bootstrap','ui.router','ngResource','testRest.controllers','testRest.services']);

angular.module('testRest').config(function($stateProvider,$httpProvider){
    $stateProvider.state('guest',{
        url:'/welcome?clientType',
        templateUrl:'coupon.web/partials/welcome.html',
        controller:'LoginController'
    }).state('login',{
        url:'/login',
        templateUrl:'coupon.web/partials/login.html',
        controller:'LoginController'
    }).state('customers',{
        url:'/customers',
        templateUrl:'coupon.web/partials/customers.html',
        controller:'CustomerListController'
    }).state('viewCustomer',{
       url:'/customers/:id/view',
       templateUrl:'coupon.web/partials/customer-view.html',
       controller:'CustomerViewController'
    }).state('newCustomer',{
        url:'/customers/new',
        templateUrl:'coupon.web/partials/customer-add.html',
        controller:'CustomerCreateController'
    }).state('editCustomer',{
        url:'/customers/:id/edit',
        templateUrl:'coupon.web/partials/customer-edit.html',
        controller:'CustomerEditController'
    });
}).run(function($state){
   $state.go('guest');
});