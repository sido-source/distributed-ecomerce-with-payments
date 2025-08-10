CREATE TABLE payment_error_record (
id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
instruction_id    UUID NOT NULL,
step              TEXT NOT NULL,
category          TEXT NOT NULL,            -- 'BUSINESS' or 'SERVICE'
error_code        TEXT NOT NULL,
error_message     TEXT NOT NULL,
provider          TEXT,
psp_reference     TEXT,
timestamp         TIMESTAMPTZ NOT NULL,
raw_response      TEXT
);

CREATE INDEX idx_per_instruction ON payment_error_record(instruction_id);
CREATE INDEX idx_per_category   ON payment_error_record(category);
CREATE INDEX idx_per_code       ON payment_error_record(error_code);
