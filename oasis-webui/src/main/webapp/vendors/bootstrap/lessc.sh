 #!/bin/bash
lessc less/bootstrap.less css/bootstrap.css
lessc -x less/bootstrap.less css/bootstrap.min.css

lessc less/responsive.less css/bootstrap-responsive.css
lessc -x less/responsive.less css/bootstrap-responsive.min.css