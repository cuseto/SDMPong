# Definition of Done for every Sprint

A single Product Backlog item is Done when:

- all acceptance criteria are satisfied;
- relevant automated tests exist and pass;
- the complete test suite passes locally;
- continuous integration passes;
- another developer has reviewed the change;
- all the relevant code is integrated into the `dev` branch;
- the application can be built from a clean clone;
- the completed behaviour can be demonstrated;
- relevant documentation is updated;
- no known critical defect remains.

The whole Sprint is Done when:

- every Product Backlog in the Sprint is Done;
- the Sprint's Milestone is closed and does not contain any open Issue;
- all the relevant code is integrated into the `dev` branch;
  - before merging into `main`: update the `version` number in build.gradle, following the format: 
  - Sprint 1 $\to$ `0.1`, Sprint 2 $\to$ `0.2`, final Sprint $\to$ `1.0`;
- the final code is merged into the `main` branch;
- the merge into the `main` branch successfully triggers a GitHub Release;
- the Sprint's related Markdown note is correctly updated by the team members.

