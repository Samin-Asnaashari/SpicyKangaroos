'use strict';

/**
 * Angular application with dependencies
 */
angular.module('appView',
    [
        'appServiceAPI',
        'ui.router',
        'appComponent.navigationView',
        'appComponent.pageView',
        'appComponent.templateView',
        'appComponent.resize'
    ]
);