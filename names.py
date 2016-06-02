

class Names:
    def __init__(self, symbols, style):
        self.symbols = symbols
        self.style = style

    def guardLabel(self, guarding_actor_id, guarded_actor_id, action_id):
        if self.style == 'mcrl2':
            return guarding_actor_id + '_allows_' + guarded_actor_id + '_' + action_id

    def actionLabel(self, instance_id, action_id):
        if self.style == 'mcrl2':
            return instance_id + '_' + action_id
        else:
            label = self.camelcase(instance_id + '_' + action_id)
            return label[0].lower() + label[1:]

    def commLabel(self, instance_id, action_id):
        actor = self._getActor(instance_id)
        action = self._getAction(actor['identifier'], action_id)

        if self.style == 'mcrl2':
            action_label = instance_id + '_' + action_id
            if len(action['guards']) > 0:
                return 'perform_' + action_label
            else:
                return action_label

    def sort(self, sort):
        l = self.camelcase(sort)
        return l[0].lower() + l[1:]

    def className(self, cls):
        return self.camelcase(cls)

    def _getActor(self, instance_id):
        for actor_id, actor in self.symbols['actors'].iteritems():
            if instance_id in actor['instances']:
                return {
                    'identifier': actor_id,
                    'data': actor
                }

        return None

    def _getAction(self, actor_id, action_id):
        for act_id, action in self.symbols['actions'].iteritems():
            if action['identifier'] == action_id and action['actor'] == actor_id:
                return action

        return None

    def camelcase(self, string):
        camelCased = ''
        parts = string.split('_')
        for part in parts:
            camelCased += part[0].upper() + part[1:]

        return camelCased
