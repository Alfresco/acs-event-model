# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.1.0-A.3] - 2026-09-28

### Added

- `org.alfresco.event.node.Deleted` events now carry an optional `isPermanentlyDeleted` flag in `data.resource`. (ACS-12674)
  `true` means the node was permanently deleted, `false` means it was moved to the trashcan.
  The flag is omitted when the emitting ACS version cannot tell the two apart, so treat a missing value as "unknown".
  Available in the `nodeDeleted` JSON schema and on `NodeResource` (`isPermanentlyDeleted()`, `Builder.setIsPermanentlyDeleted(Boolean)`).

### Fixed

- `NodeResource.equals()` now compares `primaryAssocQName`, matching `hashCode()`. (ACS-12674)
  Two resources that differ only by primary association QName are no longer considered equal.

## [1.1.0-A.2]

_Earlier releases were not tracked in this file._

[Unreleased]: https://github.com/Alfresco/acs-event-model/compare/1.1.0-A.3...HEAD
[1.1.0-A.3]: https://github.com/Alfresco/acs-event-model/compare/1.1.0-A.2...1.1.0-A.3
[1.1.0-A.2]: https://github.com/Alfresco/acs-event-model/releases/tag/1.1.0-A.2
