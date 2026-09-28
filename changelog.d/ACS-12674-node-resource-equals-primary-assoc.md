---
type: fixed
issues: [ACS-12674]
component: (optional to specify the subcomponent in larger projects?)
breaking: false
audience: public
---
`NodeResource.equals()` now compares `primaryAssocQName`, matching `hashCode()`.
Two resources that differ only by primary association QName are no longer considered equal.
