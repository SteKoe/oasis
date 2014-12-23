var gulp = require('gulp'),
    compass = require('gulp-compass');

var CONFIG = {
}

gulp.task('less', function(){
    return gulp.src(['./less/oasis.less','./less/tinymce.less'])
        .pipe(less())
        .pipe(gulp.dest('./tmp/'));
});

gulp.task('compass', function(){
    var COMPASS_CONF = {
        project: __dirname
    };

    return gulp.src('./sass/*.scss')
        .pipe(compass(COMPASS_CONF))
        .pipe(gulp.dest('./css'));
});