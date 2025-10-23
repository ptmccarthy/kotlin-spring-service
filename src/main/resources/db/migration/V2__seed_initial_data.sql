-- Seed initial reference data
-- This migration adds some basic users for production/reference purposes

INSERT INTO users (name, email, created_at) VALUES
    ('Admin User', 'admin@example.com', NOW()),
    ('System User', 'system@example.com', NOW()),
    ('Support User', 'support@example.com', NOW())
ON CONFLICT (email) DO NOTHING;
