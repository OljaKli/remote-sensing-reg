-- ALTER TABLE photo
--   ADD FOREIGN KEY (folderId)
--   references photoFolder(folderId);


DROP TABLE photo;
DROP  TABLE photoFolder;
DROP  TYPE photoType;

CREATE TABLE photo (
  photoId       INTEGER NOT NULL PRIMARY KEY,
  folderId  INTEGER references photoFolder(folderId),
  centerCoord  POINT,
  extent     GEOMETRY ,
  fileName  VARCHAR(2048),
  time TIMESTAMP
);

--- CREATE TYPE photoType AS ENUM ('RGB', 'RAW', 'NDVI', 'PSEUDO_RGB', 'THERMO');

CREATE TABLE photoFolder (
  folderId       INTEGER NOT NULL PRIMARY KEY,
  folderPath  TEXT,
  photoType  INTEGER,
  extend     GEOMETRY
)