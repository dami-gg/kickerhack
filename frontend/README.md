# Kicker Hack
This project was generated with Generator-M-Ionic v1.4.1. For more info visit the [repository](https://github.com/mwaylabs/generator-m-ionic) or check out the README below.

## Development

First install `NodeJS`. Then prepare your environment.

    npm install
    npm install bower
    bower install

Most of the webapp's functionality can be tested in the browser during development.
    
    gulp watch

Some of the stuff (check-in and OAuth login) are only working when deployed to a mobile device. Before this will work you need to have the Android SDK installed (API Level 19). Be sure to enable USB-Debugging on your device.

    gulp --cordova 'run android'

## Building and Deploying

Use the `release.sh` script to prepare the deployment.