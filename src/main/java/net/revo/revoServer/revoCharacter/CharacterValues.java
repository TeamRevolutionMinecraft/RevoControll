package net.revo.revoServer.revoCharacter;

import static net.revo.revoServer.revoCharacter.CharValueDataType.INTEGER;
import static net.revo.revoServer.revoCharacter.CharValueDataType.STRING;

public enum CharacterValues
{
    STRENGHT(INTEGER),
    LIFEPOINTS(INTEGER),
    NAME(STRING)
    ;


    private final String yaml_string;
    private final CharValueDataType type;

    CharacterValues(CharValueDataType type)
    {
        this.type = type;
        this.yaml_string = "CharacterValues." + name();
    }
    public String getYaml_string()
    {
        return yaml_string;
    }

    public CharValueDataType getType()
    {
        return type;
    }
}
