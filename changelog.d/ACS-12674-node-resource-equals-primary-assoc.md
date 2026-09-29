---
type: fixed
title: "`NodeResource.equals()` now compares `primaryAssocQName`"
issues: [ACS-12674]
component: (optional to specify the subcomponent in larger projects?)
breaking: false
audience: public
---
`primaryAssocQName` was already part of `NodeResource.hashCode()` but was missing from `equals()`.
Two resources that differed only by primary association QName were therefore considered equal,
which broke the `equals`/`hashCode` contract.

**Impact.** Code that compares `NodeResource` instances, or keeps them in `Set`s or as `Map` keys,
may now treat resources as different where it previously treated them as equal. No change to the JSON payload.
