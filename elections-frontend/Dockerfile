# Frontend Build Stage
FROM node:18 AS frontend-builder

WORKDIR /app

# Copy dependency files for caching
COPY package.json package-lock.json ./

# Install dependencies in production mode
RUN npm ci

# Copy application source and build
COPY ./ ./
RUN npm run build

# Frontend Runtime Stage
FROM nginx:alpine AS frontend-runtime

# Copy built frontend files
COPY --from=frontend-builder /app/dist /usr/share/nginx/html

# Add custom Nginx configuration
RUN echo 'server { \
    listen 80; \
    location / { \
        try_files $uri /index.html; \
    } \
}' > /etc/nginx/conf.d/default.conf

# Expose nginx port
EXPOSE 80

# Run nginx
CMD ["nginx", "-g", "daemon off;"]
