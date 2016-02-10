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

    //ADMIN-COMPANIES
    .state('companies',{
        url:'/companies',
        templateUrl:'coupon.web/partials/companies.html',
        controller:'CompanyListController'
    }).state('viewCompany',{
       url:'/companies/:id/view',
       templateUrl:'coupon.web/partials/company-view.html',
       controller:'CompanyViewController'
    }).state('newCompany',{
        url:'/companies/new',
        templateUrl:'coupon.web/partials/company-add.html',
        controller:'CompanyCreateController'
    }).state('editCompany',{
        url:'/companies/:id/edit',
        templateUrl:'coupon.web/partials/company-edit.html',
        controller:'CompanyEditController'
    })
    
    //ADMIN - REPORTS
    .state('reportAllInvoices',{
        url:'/admin/allInvoices',
        templateUrl:'coupon.web/partials/invoices-report-simple.html',
        controller:'InvoicesSimpleReportController',
        data: {adminMode:"1"}
    }).state('reportInvoicesByCustomer',{
        url:'/admin/allInvoicesByCustomer',
        templateUrl:'coupon.web/partials/report-by-customer.html',
        controller:'InvoicesByPartyReportController',
        data: {adminMode:"1", party: "customer"}
    }).state('reportInvoicesByCompany',{
        url:'/admin/allInvoicesByCompany',
        templateUrl:'coupon.web/partials/report-by-company.html',
        controller:'InvoicesByPartyReportController',
        data: {adminMode:"1", party: "company"}
    })
    
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
    }).state('reportCompanyInvoices',{
        url:'/company/coupons/companyInvoices',
        templateUrl:'coupon.web/partials/invoices-report-simple.html',
        controller:'InvoicesSimpleReportController'
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
    }).state('reportCustomerInvoices',{
        url:'/customer/coupons/customerInvoices',
        templateUrl:'coupon.web/partials/invoices-report-simple.html',
        controller:'InvoicesSimpleReportController'
    });
   
    
}).run(function($state){
   $state.go('guest');
});