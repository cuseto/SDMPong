# Branch management and hierarchy

- `main`: serves as a release branch. At the end of every Sprint, the code is merged from `dev` into `main`, triggering a GitHub Release, so that the Client has access to a functioning prototype. It is intended to be the "commercial" branch, where the product is showcased to the customer.
- `dev`: serves as the main development branch. Its CI/CD workflow does not involve a Release. At the end of every Sprint, before merging into `main`, every Product Backlog item and/or GitHub Issue of the active Sprint must be merged into `dev`.
- `feature branches`: branches dedicated to single issues or features, to be merged into `dev` before the Sprint's end.
- `docs`: the documentation branch. Hosts both technical and non-technical documentation

# Milestones and Issues
### Milestones
- A GitHub Milestones is created for each Sprint, to more rigorously track down Backlog items and other issues that may arise during coding
- A Milestone can be closed only if every Issue is marked as "Closed", and if every team member agrees that there is no more work to do for that specific Milestone.
- Given the volatile nature of code requirements, a **Milestone's Issues** and the **Backlog items listed in the SPRINT\_\*.md files** are not intended to be perfectly overlapping. SPRINT\_\*.md documents aim to provide an initial description of a Sprint's requirents; Issues may add to those initial requirement if necessary, always without diverging from the Sprint's goal.

### Issues
- Issues can be opened without necessarily having an immediate assignee;
- If no assignee can be found in a reasonable amount of time (due to being busy with other work), the SCRUM Master is assigned to the Issue;