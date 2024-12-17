# Frontend Build Stage
FROM node:18 AS frontend-builder

WORKDIR /app

# Copy dependency files for caching
COPY elections-frontend/package.json elections-frontend/package-lock.json ./

# Install dependencies in production mode
RUN npm ci

# Copy application source and build
COPY elections-frontend/ ./
RUN npm run build

# Frontend Runtime Stage
FROM nginx:alpine AS frontend-runtime

# Copy built frontend files
COPY --from=frontend-builder /app/dist /usr/share/nginx/html

# Expose nginx port
EXPOSE 80

# Run nginx
CMD ["nginx", "-g", "daemon off;"]
