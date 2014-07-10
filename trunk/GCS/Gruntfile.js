module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    less: {
        development: {
            options: {
                paths: ["war/less"]
            },
            files: [{"war/css/main.css": "war/less/main.less"},{"war/css/users.css": "war/less/users.less"}]
        }
    },
    cssmin: {
      add_banner: {
        options: {
          banner: '/* BBVA Global Customer Solutions minified css file */'
        },
        files: {
          'war/css/main.css': ['war/css/main.css']
        }
      }
    },
    watch: {
      grunt: { files: ['Gruntfile.js'] },

      less: {
        files: 'war/less/*.less',
        tasks: ['less']
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
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('default', ['less','cssmin','watch']);
}