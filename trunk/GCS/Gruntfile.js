module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    less: {
        development: {
            options: {
                paths: ["war/less"]
            },
            files: [{"war/css/styles.css": "war/less/styles.less"}]
        }
    },
    cssmin: {
      add_banner: {
        options: {
          banner: '/* BBVA Global Customer Solutions minified css file */'
        },
        files: {
          // output : input
          'war/css/styles.min.css': ['war/css/styles.css']
        }
      }
    },
    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
        '<%= grunt.template.today("yyyy-mm-dd") %> */',
        mangle: false
      },
      my_target: {
        files: {
          'war/js/prod/scripts.min.js': ['war/js/*.js']
        }
      }
    },
    watch: {
      less: {
        files: 'war/less/**/*.less',
        tasks: ['less', 'cssmin']
      },
      uglify: {
        files: 'war/js/*.js',
        tasks: ['uglify']
      },
      livereload: {
        // Here we watch the files the less task will compile to
        // These files are sent to the live reload server after less compiles to them
        options: { livereload: true },
        files: ['war/less/**/*.less', 'war/jsp/**/*.jsp', 'war/js/**/*.js'],
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('default', ['less','cssmin','uglify','watch']);
}