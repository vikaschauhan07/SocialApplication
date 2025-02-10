CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(55) DEFAULT NULL,
    user_name VARCHAR(55) UNIQUE NOT NULL,
    email VARCHAR(125) UNIQUE NOT NULL,
    email_verified SMALLINT DEFAULT 0,
    password VARCHAR(255) DEFAULT NULL,
    phone_code VARCHAR(10) DEFAULT NULL,
    iso_code VARCHAR(10) DEFAULT NULL,
    phone_number VARCHAR(15) DEFAULT NULL,
    profile_image VARCHAR(255) DEFAULT NULL,
    profile_cover_image VARCHAR(255) DEFAULT NULL,
    theme VARCHAR(255) DEFAULT NULL,
    dob TIMESTAMP DEFAULT NULL,
    location VARCHAR(255) DEFAULT NULL,
    latitude VARCHAR(255) DEFAULT NULL,
    longitude VARCHAR(255) DEFAULT NULL,
    language VARCHAR(255) DEFAULT '0' CHECK (language IN ('0', '1')),
    hobbies VARCHAR(255) DEFAULT NULL,
    links VARCHAR(255) DEFAULT NULL,
    bio VARCHAR(255) DEFAULT NULL,
    gender SMALLINT DEFAULT NULL CHECK (gender IN (0, 1)),
    private_account SMALLINT DEFAULT 0 CHECK (private_account IN (0, 1)),
    is_verified INT DEFAULT 0,
    notification_status SMALLINT DEFAULT 1 CHECK (notification_status IN (0, 1)),
    role INT DEFAULT 1 CHECK (role IN (0, 1)),
    online_status INT DEFAULT 1 CHECK (online_status IN (0, 1)),
    socket_id VARCHAR(255) DEFAULT NULL,
    status INT DEFAULT 1 CHECK (status IN (0, 1)),
    terms_condition BOOLEAN DEFAULT '0' CHECK (terms_condition IN ('0', '1')),
    otp VARCHAR(6) DEFAULT NULL,
    fcm_token VARCHAR(255) DEFAULT NULL,
    device_type SMALLINT DEFAULT 1 CHECK (device_type IN (0, 1)),
    can_share_story SMALLINT DEFAULT 1 CHECK (can_share_story IN (1, 2)),
    remember_token VARCHAR(100) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);

COMMENT ON COLUMN users.email_verified IS '1=>yes,0=>no';
COMMENT ON COLUMN users.language IS '0 =>english, 1=>albalian';
COMMENT ON COLUMN users.gender IS '1=>male,0=>female';
COMMENT ON COLUMN users.private_account IS '1=>yes,0=>no';
COMMENT ON COLUMN users.notification_status IS '1=>yes,0=>no';
COMMENT ON COLUMN users.role IS '0 =>admin, 1=>user';
COMMENT ON COLUMN users.online_status IS '0 =>inactive, 1=>active';
COMMENT ON COLUMN users.status IS '0 =>inactive, 1=>active';
COMMENT ON COLUMN users.terms_condition IS '1 : accept 0: reject';
COMMENT ON COLUMN users.device_type IS '1->Android,0->ios';
COMMENT ON COLUMN users.can_share_story IS '1=> friends cant share, 2=>friends can share';
