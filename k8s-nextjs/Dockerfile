# Stage 1: Build the Next.js application
FROM node:20 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json/yarn.lock into the working directory
COPY package*.json ./
# If you're using yarn, use this instead:
# COPY yarn.lock ./

# Install dependencies
RUN npm install
# If you're using yarn, use this instead:
# RUN yarn install

# Copy the rest of the application code into the container
COPY . .

# Build the Next.js app
RUN npm run build
# If you're using yarn, use this instead:
# RUN yarn build

# Stage 2: Production environment
FROM node:20 AS runner

# Create a non-root user
RUN addgroup --system --gid 1001 nodejs 
RUN adduser --system --uid 1001 nextjs

# Set environment variable to use the Next.js production environment
ENV NODE_ENV=production

# Set the working directory inside the container
WORKDIR /app

# Copy the build output from the builder stage to the runner stage
COPY --from=builder /app ./

# Install production dependencies (only what's needed for running the app)
RUN npm install --only=production
# If you're using yarn, use this instead:
# RUN yarn install --production

# Change ownership of the working directory to the non-root user
RUN chown -R nextjs:nodejs /app

# Switch to the non-root user
USER nextjs

# Expose the port on which Next.js will run (default is 3000)
EXPOSE 3000

# Start the Next.js app
CMD ["npm", "start"]