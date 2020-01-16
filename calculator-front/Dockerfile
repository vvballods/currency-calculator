FROM node:alpine as BUILD

COPY . /app
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

RUN yarn

RUN yarn build

FROM nginx:alpine

COPY --from=BUILD /app/build /usr/share/nginx/html
# --------- only for those using react router ----------
# if you are using react router
# you need to overwrite the default nginx configurations
# remove default nginx configuration file
RUN rm /etc/nginx/conf.d/default.conf
# replace with custom one
COPY nginx.conf /etc/nginx/conf.d
# --------- /only for those using react router ----------
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]