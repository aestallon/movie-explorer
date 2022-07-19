/**
 * This package contains a simple script for transferring the contents of
 * the 'imdb_top_1000.csv' file to an Apache Derby server database.
 *
 * <p>Some movie records have longer 'short' descriptions than 255 characters.
 * The database will try to truncate these entries. The appearing exceptions
 * are harmless, do not fret (although the descriptions will be truncated
 * nonetheless).
 *
 * @author Szabolcs Bazil Papp
 * @version 1.0
 * @since 2022-07-19
 */
package hu.aestallon.movieproject.dbuploader;