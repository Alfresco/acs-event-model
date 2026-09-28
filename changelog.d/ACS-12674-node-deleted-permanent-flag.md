---
type: added
issues: [ACS-12674]
component: (optional to specify the subcomponent in larger projects?)
breaking: false
audience: public
---
`org.alfresco.event.node.Deleted` events now carry an optional `isPermanentlyDeleted` flag in `data.resource`.
`true` means the node was permanently deleted, `false` means it was moved to the trashcan.
The flag is omitted when the emitting ACS version cannot tell the two apart, so treat a missing value as "unknown".
Available in the `nodeDeleted` JSON schema and on `NodeResource` (`isPermanentlyDeleted()`, `Builder.setIsPermanentlyDeleted(Boolean)`).
