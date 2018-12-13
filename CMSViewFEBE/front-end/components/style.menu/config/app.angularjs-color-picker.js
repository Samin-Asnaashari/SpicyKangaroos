/**
 * angular color picker configuration
 */
angular.module('appComponent.styleMenu').config(function ($provide) {
    $provide.decorator('ColorPickerOptions', function ($delegate) {
        var options = angular.copy($delegate);
        options.round = false;
        options.alpha = true;
        options.format = 'rgb'; //['hex', 'hsl', 'hsv', 'rgb', 'hex8'];
        options.inline = true;
        options.pos = 'bottom left';
        // options.close = false;
        // options.reset = false;
        // options.clear = false;
        options.swatchOnly = false;
        options.swatchBootstrap = true;
        return options;
    });
});