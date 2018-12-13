
// TODO: Fix all-in-one JS, etc. For now just make sure we understand the structure.
// TODO: Put the general task (bower) and (shared components) in here since it should be executed only once.

var gulp = require('gulp');
var connect = require('gulp-connect');
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var minifyCSS = require('gulp-minify-css');
var clean = require('gulp-clean');
var runSequence = require('run-sequence');
var clear = require('clear');

var publicURL = './public/app-view';

var public = {
    'styles': publicURL + '/css',
    'scripts': publicURL + '/js'
};

var cleanPaths = [
    public.styles + '/*',
    public.scripts + '/*'
];

var cors = function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Headers', '*');
    next();
};

/**
 * Tasks
 */

gulp.task('lint-view', function () {
    gulp.src(['./app-view/**/*.js', '!./bower_components/**'])
        .pipe(jshint())
        .pipe(jshint.reporter('default'))
        .pipe(jshint.reporter('fail'));
});

gulp.task('clean-view', function () {
    clear();
    return gulp
        .src(cleanPaths, {read: false})
        .pipe(clean());
});

gulp.task('minify-css-view', function () {
    var opts = {comments: true, spare: true};
    gulp.src(['./app-view/**/*.css', '!./bower_components/**'])
        .pipe(minifyCSS(opts))
        .pipe(gulp.dest('./public/app-view/'))
});

gulp.task('minify-js-view', function () {
    gulp.src(['./app-view/**/*.js', '!./bower_components/**'])
        .pipe(gulp.dest('./public/app-view/'))
});

gulp.task('copy-bower-components-view', function () {
    gulp.src('./bower_components/**')
        .pipe(gulp.dest('public/bower_components'));
});

gulp.task('copy-html-files-view', function () {
    gulp.src('./app-view/**/*.html')
        .pipe(gulp.dest('./public/app-view/'));
});

gulp.task('connect-view', function () {
    connect.server({
        root: 'app-view/',
        port: 8882,
        middleware: function(connect) {
            return [
                connect().use('/bower_components', connect.static('bower_components')),
                connect().use('/components', connect.static('components')),
                connect().use('/services', connect.static('services')),
                connect().use('/asset', connect.static('asset'))
            ];
        }
    });
});

gulp.task('connect-public-view', function () {
    connect.server({
        root: 'public/app-view/',
        port: 9992,
        middleware: function(connect) {
            return [
                connect().use('/bower_components', connect.static('bower_components')),
                connect().use('/components', connect.static('components')),
                connect().use('/services', connect.static('services')),
                connect().use('/asset', connect.static('asset'))
            ];
        }
    });
});

/**
 * Run sequence tasks
 */

gulp.task('build-view', function () {
    runSequence(
        ['clean-view', 'lint-view', 'minify-css-view', 'minify-js-view', 'copy-html-files-view',
            'copy-bower-components-view', 'connect-public-view']
    );
});