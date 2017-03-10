DROP TABLE photo;
DROP TABLE photoFolder;
DROP TYPE photoType;

CREATE TABLE photo (
  photoId       INTEGER NOT NULL PRIMARY KEY,
  folderId  INTEGER,
  centerCoord  POINT,
  extent     GEOMETRY ,
  fileName  VARCHAR(2048),
  time TIMESTAMP
);

CREATE TYPE photoType AS ENUM ('RGB', 'RAW', 'NDVI', 'PSEUDO_RGB', 'THERMO');

CREATE TABLE photoFolder (
  folderId       INTEGER NOT NULL PRIMARY KEY,
  folderPath  TEXT,
  photoType  photoType,
  extend     GEOMETRY
)