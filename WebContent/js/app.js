/**
 * Inspired by Sandeep on 01/06/14.
 */

angular.module('testRest',['ui.bootstrap','ui.router','ui.bootstrap.showErrors','ui.uploader','ngResource','ngCookies','testRest.controllers','testRest.services']);

angular.module('testRest').config(function($stateProvider,$httpProvider){
    $stateProvider.state('guest',{
        url:'/welcome?clientType',
        templateUrl:'coupon.web/partials/welcome.html',
        controller:'LoginController'
    }).state('login',{
        url:'/login',
        templateUrl:'coupon.web/partials/login.html',
        controller:'LoginController'
    })
    
    //ADMIN-CUSTOMERS
    .state('customers',{
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
    })
    //TODO  ADMIN-COMPANIES
    
    
    //COMPANY-COUPONS
    .state('company-coupons',{
        url:'/company/coupons',
        templateUrl:'coupon.web/partials/company-coupons.html',
        controller:'CompanyCouponListController'
    }).state('viewCompanyCoupon',{
       url:'/company/coupons/:id/view',
       templateUrl:'coupon.web/partials/company-coupon-view.html',
       controller:'CompanyCouponViewController'
    }).state('newCoupon',{
        url:'/company/coupons/new',
        templateUrl:'coupon.web/partials/coupon-add.html',
        controller:'CouponCreateController'
    }).state('editCoupon',{
        url:'/company/coupons/:id/edit',
        templateUrl:'coupon.web/partials/coupon-edit.html',
        controller:'CouponEditController'
    })
    
   //CUSTOMER-COUPONS 
    .state('customer-coupons',{
        url:'/customer/coupons',
        templateUrl:'coupon.web/partials/customer-coupons.html',
        controller:'CustomerCouponListController'
    }).state('viewCustomerCoupon',{
       url:'/customer/coupons/:id/view',
       templateUrl:'coupon.web/partials/customer-coupon-view.html',
       controller:'CustomerCouponViewController'
    }).state('purchaseCoupon',{
        url:'/customer/coupons/purchase',
        templateUrl:'coupon.web/partials/coupon-purchase.html',
        controller:'PurchaseCouponController'
    });
   
    
}).run(function($state){
   $state.go('guest');
});