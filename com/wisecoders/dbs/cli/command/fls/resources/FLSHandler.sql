:::CreateSequence
CREATE SEQUENCE IF NOT EXISTS seq_id

:::CreateTable
CREATE TABLE IF NOT EXISTS sessions (
    id                   integer NOT NULL PRIMARY KEY,
	username             varchar(120) NOT NULL,
    start_date           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    active               boolean DEFAULT true,
    last_ping_date       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
 );

:::GetNextId
SELECT NEXT VALUE FOR seq_id

:::CreateSession
INSERT INTO sessions(username,id,active) VALUES (?,?,true)

:::UpdateSession
UPDATE sessions SET last_ping_date=CURRENT_TIMESTAMP WHERE id=?

:::RefreshSessions
UPDATE sessions SET active = false WHERE active=true and last_ping_date < TIMESTAMPADD('MINUTE', -3, CURRENT_TIMESTAMP)

:::Unregister
UPDATE sessions SET active = false WHERE id=?

:::GetActiveCount
SELECT count(*) FROM sessions WHERE active =true

:::ListActiveSessions
SELECT username, start_date FROM sessions WHERE active=true

:::History
SELECT id, active, formatdatetime(start_date,'yyyy-MM-dd HH:mm:ss') as StartDate, formatdatetime(last_ping_date,'yyyy-MM-dd HH:mm:ss') as LastPingDate FROM sessions ORDER BY start_date