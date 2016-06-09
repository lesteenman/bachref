

class Names:
    def __init__(self, symbols, style):
        self.symbols = symbols
        self.style = style

    def guardLabel(self, guarding_actor_id, guarded_actor_id, action_id):
        if self.style == 'mcrl2':
            return guarding_actor_id + '_allows_' + guarded_actor_id + '_' + action_id
        else:
            label = 'allows_' + guarded_actor_id + '_' + action_id
            if guarding_actor_id:
                label = guarding_actor_id + '_' + label
            label = self.camelcase(label, True)
            return label[0].lower() + label[1:]

    def actionLabel(self, instance_id, action_id):
        if self.style == 'mcrl2':
            return instance_id + '_' + action_id
        else:
            label = self.camelcase(instance_id + '_' + action_id)
            return label[0].lower() + label[1:]

    def commLabel(self, instance_id, action_id):
        actor = self._getActor(instance_id)
        action = self._getAction(actor['identifier'], action_id)
        guards = self._getGuards(actor['identifier'], instance_id, action_id)

        if self.style == 'mcrl2':
            action_label = instance_id + '_' + action_id
            if len(guards) > 0:
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

    def _getGuards(self, actor_id, instance_id, action_id):
        for actor_id2, actor in self.symbols['guards'].iteritems():
            for instance_id2, instance in actor.iteritems():
                if actor_id2 == actor_id and instance_id2 == instance_id and action_id in instance:
                    return instance[action_id]

        return []

    def camelcase(self, string, lcFirst=False):
        camelCased = ''
        parts = string.split('_')
        for part in parts:
            camelCased += part[0].upper() + part[1:]

        if lcFirst:
            return camelCased[0].lower() + camelCased[1:]
        else:
            return camelCased
