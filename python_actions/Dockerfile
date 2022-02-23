# The following sources were used for the docker container:
#
# [1] https://docs.github.com/en/actions/creating-actions/creating-a-docker-container-action
# [2] https://dockerlabs.collabnix.com/beginners/dockerfile/lab_dockerfile_python.html

FROM python:3.8-slim-buster


COPY read_CODEOWNERS.py /read_CODEOWNERS.py
COPY CI_CODEOWNERS /CODEOWNERS

CMD ["/read_CODEOWNERS.py"]
ENTRYPOINT ["python"]
