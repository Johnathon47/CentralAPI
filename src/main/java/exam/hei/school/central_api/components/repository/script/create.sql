-- Championship
CREATE TABLE championship (
  id SERIAL PRIMARY KEY,
  name VARCHAR(30) UNIQUE NOT NULL CHECK (
    name IN ('CHAMPIONAT1', 'CHAMPIONAT2')
  )
);

-- Player :
CREATE TABLE player (
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  number INTEGER,
  position VARCHAR(20) NOT NULL CHECK (
    position IN ('STRIKER', 'MIDFIELDER', 'DEFENSE', 'GOAL_KEEPER')
  ),
  nationality VARCHAR(50),
  age INTEGER,
  championship_id INTEGER REFERENCES championship(id),
  scored_goals INTEGER DEFAULT 0,
  playing_time_value NUMERIC,
  playing_time_unit VARCHAR(10) NOT NULL CHECK (
    playing_time_unit IN ('SECOND', 'MINUTE', 'HOUR')
  )
);


-- Coach :
CREATE TABLE coach (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  nationality VARCHAR(50)
);

-- Club :
CREATE TABLE club (
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  acronym CHAR(3),
  year_creation INTEGER,
  stadium VARCHAR(100),
  coach_id INTEGER REFERENCES coach(id)
);


-- club_statistics :
CREATE TABLE club_statistics (
  id SERIAL PRIMARY KEY,
  club_id UUID REFERENCES club(id),
  championship_id INTEGER REFERENCES championship(id),
  scored_goals INTEGER,
  conceded_goals INTEGER,
  difference_goals INTEGER,
  clean_sheet_number INTEGER,
  ranking_points INTEGER
);


-- championship_ranking :
CREATE TABLE championship_ranking (
  id SERIAL PRIMARY KEY,
  championship_id INTEGER REFERENCES championship(id),
  rank INTEGER,
  difference_goals_median NUMERIC
);

