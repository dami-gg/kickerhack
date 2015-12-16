FROM nginx
MAINTAINER Zalando <hackweek@zalando.de>

COPY platforms/browser/www /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080