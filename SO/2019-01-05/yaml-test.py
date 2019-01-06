#!/usr/bin/env python3.6

import yaml
import sys

class RefTag(yaml.YAMLObject):
  yaml_tag = u'Ref'
  def __init__(self, text):
    self.text = text
  def __repr__(self):
    return "%s( text=%r)" % ( self.__class__.__name__, self.text)
  @classmethod
  def from_yaml(cls, loader, node):
    return RefTag(node.value)
  @classmethod
  def to_yaml(cls, dumper, data):
    return dumper.represent_scalar(cls.yaml_tag, data.text)
yaml.SafeLoader.add_constructor('!Ref', RefTag.from_yaml)
yaml.SafeDumper.add_multi_representer(RefTag, RefTag.to_yaml)

yaml_list = None
with open("./yaml-test.yml", "r")  as file:  
  try:
    yaml_list = yaml.safe_load(file)
  except yaml.YAMLError as exc:
    print ("--", exc)
    sys.exit(1)

print (yaml.dump(yaml_list, default_flow_style=False))




