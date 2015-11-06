package berlin.utils;

import java.util.EventListener;
import java.util.List;

import berlin.infos.InfosInfos;
import berlin.infos.MapInfos;
import berlin.infos.MoveInfos;
import berlin.infos.StateInfos;


public interface ActionListener extends EventListener
{
	public void onGameStart(InfosInfos infos, MapInfos mapInfos, List<StateInfos> states);
    public List<MoveInfos> onTurn(String gameId, Integer turnId, List<StateInfos> states);
    public void onGameOver(String gameId);
    public void onPing(InfosInfos infos);
}
