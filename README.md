# Quill-PG

quill-pg contains Postgres (JDBC and postgres-async) extensions for [quill](https://github.com/getquill).
It is analogous to [slick-pg](https://github.com/tminglei/slick-pg) for [slick](https://github.com/slick/slick)

## Current state

quill-pg is currently in alpha phase. It is waiting for some futures in currently unreleased
versions of quill and it needs to be tested properly

## Supported Extensions

- [X] Json/Jsonb
- [ ] Arrays (waiting for quill 1.0.1 to implement completely generic arrays, see https://github.com/getquill/quill/pull/588)
- [X] HStore

## What is done

- [X] Generic dependency/project structure
- [ ] Tests
- [ ] Better documentation

## Code formatting

Please make sure that you format the code using scalafmt. You can do this by running scalafmt in sbt before committing.
See scalafmt for more info.

## License

See the [license](https://github.com/mdedetrich/quill-pg/blob/master/LICENSE.txt) file for details.
